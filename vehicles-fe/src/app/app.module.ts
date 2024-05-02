import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppComponent } from './app.component';
import { WebsocketTestComponent } from './websocket-test/websocket-test.component';
import { StompRService } from '@stomp/ng2-stompjs';

@NgModule({
  declarations: [AppComponent, WebsocketTestComponent],
  imports: [BrowserModule],
  providers: [StompRService],
  bootstrap: [AppComponent],
})
export class AppModule {}
