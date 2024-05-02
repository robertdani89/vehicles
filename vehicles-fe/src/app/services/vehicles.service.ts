import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { firstValueFrom, Observable } from 'rxjs';

@Injectable()
export class VehiclesService {
  private baseUrl = '';

  constructor(private http: HttpClient) {}

  queryVehiclesInCircle(
    latitude: number,
    longitude: number,
    radius: number
  ): Promise<any> {
    return firstValueFrom(
      this.http.get<any>(
        `${this.baseUrl}/vehicles?latitude=${latitude}&longitude=${longitude}&radius=${radius}`
      )
    );
  }

  registerVehicle(): Observable<any> {
    return this.http.post<any>(`${this.baseUrl}/vehicles`, {});
  }

  updateVehiclePosition(
    id: string,
    latitude: number,
    longitude: number
  ): Observable<any> {
    return this.http.post<any>(`${this.baseUrl}/vehicle/${id}`, {
      latitude,
      longitude,
    });
  }

  createNotification(vehicleId: string, message: string): Observable<any> {
    return this.http.post<any>(`${this.baseUrl}/notifications`, {
      vehicleId,
      message,
    });
  }
}
