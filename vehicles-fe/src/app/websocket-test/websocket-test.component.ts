import { Component, OnInit } from '@angular/core';
import { WebSocketService } from '../services/websocket';

@Component({
  selector: 'app-websocket-test',
  templateUrl: './websocket-test.component.html',
  styleUrls: ['./websocket-test.component.scss'],
})
export class WebsocketTestComponent implements OnInit {
  public notifications = 0;

  constructor(private webSocketService: WebSocketService) {}
  ngOnInit(): void {
    this.webSocketService.onEvent().subscribe((x) => {
      console.log(x);
    });
  }
}
