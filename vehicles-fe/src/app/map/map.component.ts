import { Component, OnInit } from '@angular/core';
import { WebSocketService } from '../services/websocket';
import * as Leaflet from 'leaflet';

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.scss'],
})
export class MapComponent implements OnInit {
  public notifications = 0;

  options: Leaflet.MapOptions = {
    layers: getLayers(),
    zoom: 10,
    center: new Leaflet.LatLng(47.2587316, 19.3208951),
  };

  constructor(private webSocketService: WebSocketService) {}
  ngOnInit(): void {
    this.webSocketService.onEvent().subscribe((x) => {
      console.log(x);
    });

    setTimeout(() => {
      marker.setLatLng(new Leaflet.LatLng(47.2587316, 19.3408951));
    }, 1000);
  }
}

export const getLayers = (): Leaflet.Layer[] => {
  return [
    new Leaflet.TileLayer(
      'https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png',
      {
        attribution: '&copy; OpenStreetMap contributors',
      } as Leaflet.TileLayerOptions
    ),
    ...getMarkers(),
  ] as Leaflet.Layer[];
};

const marker = new Leaflet.Marker(new Leaflet.LatLng(47.2587316, 19.3208951), {
  icon: new Leaflet.Icon({
    iconSize: [50, 41],
    iconAnchor: [13, 41],
    iconUrl: 'assets/car.svg',
  }),
  title: 'Workspace',
} as Leaflet.MarkerOptions);

export const getMarkers = (): Leaflet.Marker[] => {
  return [
    marker,
    // new Leaflet.Marker(new Leaflet.LatLng(43.5074826, 16.4390046), {
    //   icon: new Leaflet.Icon({
    //     iconSize: [50, 41],
    //     iconAnchor: [13, 41],
    //     iconUrl: 'assets/red-marker.svg',
    //   }),
    //   title: 'Riva'
    // } as Leaflet.MarkerOptions),
  ] as Leaflet.Marker[];
};
