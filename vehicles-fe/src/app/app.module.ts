import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppComponent } from './app.component';
import { StompRService } from '@stomp/ng2-stompjs';
import { LeafletModule } from '@asymmetrik/ngx-leaflet';
import { HeaderComponent } from './header/header.component';
import { MapComponent } from './map/map.component';

@NgModule({
  declarations: [AppComponent, MapComponent, HeaderComponent],
  imports: [BrowserModule, LeafletModule],
  providers: [StompRService],
  bootstrap: [AppComponent],
})
export class AppModule {}
