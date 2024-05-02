import { AfterViewInit, Component } from '@angular/core';
import * as L from 'leaflet';
import { VehiclesService } from '../services/vehicles.service';
import { WebSocketService } from '../services/websocket.service';

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.scss'],
})
export class MapComponent implements AfterViewInit {
  private map!: L.Map;
  public notifications = 0;

  private markers: { [key: string]: L.Marker } = {};
  constructor(
    private readonly webSocketService: WebSocketService,
    private readonly vehicleService: VehiclesService
  ) {}

  ngAfterViewInit(): void {
    this.initMap();
    this.getVehicles();
    this.webSocketService.event.subscribe((body) => {
      this.updateMarker(body.id, body.latitude, body.longitude);
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

  private async getVehicles() {
    const res = await this.vehicleService.queryVehiclesInCircle(0, 0, 10000);

    res.vehicles.forEach((v: any) => {
      this.webSocketService.subscribe(v.id);
    });
  }

  private updateMarker(id: string, lat: number, long: number) {
    if (!this.markers[id]) {
      const marker = new L.Marker(new L.LatLng(lat, long), {
        icon: new L.Icon({
          iconSize: [50, 41],
          iconAnchor: [13, 41],
          iconUrl: 'https://www.svgrepo.com/show/25407/car.svg',
        }),
        title: id,
      } as L.MarkerOptions);
      this.markers[id] = marker;
      marker.addTo(this.map);
    } else {
      this.markers[id].setLatLng(new L.LatLng(lat, long));
    }
  }
}
