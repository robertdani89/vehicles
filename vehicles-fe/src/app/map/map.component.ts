import { AfterViewInit, Component } from '@angular/core';
import * as L from 'leaflet';
import { Subject, takeUntil } from 'rxjs';
import { VehiclesService } from '../services/vehicles.service';
import { MessageType, WebSocketService } from '../services/websocket.service';

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.scss'],
})
export class MapComponent implements AfterViewInit {
  private map!: L.Map;
  public notifications = 0;

  private markers: { [key: string]: L.Marker } = {};
  private $ondestroy = new Subject();

  constructor(
    private readonly webSocketService: WebSocketService,
    private readonly vehicleService: VehiclesService
  ) {}

  ngAfterViewInit(): void {
    this.initMap();
    this.getVehicles();
    this.webSocketService.event
      .pipe(takeUntil(this.$ondestroy))
      .subscribe(({ type, id, data }) => {
        switch (type) {
          case MessageType.position:
            this.updateMarkerPosition(id, data.latitude, data.longitude);
            break;
          case MessageType.notification:
            this.updateMarkerNotification(id, data);
            break;
        }
      });
  }

  private initMap(): void {
    this.map = L.map('map', {
      center: [47.2587316, 19.3208951],
      zoom: 13,
    });

    const tiles = L.tileLayer(
      'https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png',
      {
        maxZoom: 18,
        minZoom: 3,
        attribution:
          '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>',
      }
    );

    tiles.addTo(this.map);
  }

  //TODO: move behind a modal and pick vehicles one by one
  private async getVehicles() {
    const res = await this.vehicleService.queryVehiclesInCircle(0, 0, 10000);

    res.vehicles.forEach((v: any) => {
      this.webSocketService.subscribe(v.id);
    });
  }

  private updateMarkerPosition(id: string, lat: number, long: number) {
    if (!this.markers[id]) {
      const marker = new L.Marker(new L.LatLng(lat, long), {
        icon: new L.Icon({
          iconSize: [50, 41],
          iconAnchor: [13, 41],
          iconUrl: 'https://www.svgrepo.com/show/25407/car.svg',
        }),
        title: id,
      });

      this.markers[id] = marker;
      marker.addTo(this.map);
    } else {
      this.markers[id].setLatLng(new L.LatLng(lat, long));
    }
  }

  private updateMarkerNotification(id: string, message: string) {
    if (!this.markers[id]) {
      return;
    }
    this.markers[id].bindTooltip(message, {
      permanent: true,
      direction: 'right',
    });
  }
}
