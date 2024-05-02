import { Injectable, OnDestroy } from '@angular/core';
import { StompRService, StompConfig } from '@stomp/ng2-stompjs';
import SockJS from 'sockjs-client';
import { Subject, Subscription, takeUntil } from 'rxjs';

@Injectable()
export class WebSocketService implements OnDestroy {
  private subs: { [key: string]: Subscription } = {};
  private $ondestroy = new Subject();
  public event = new Subject<any>();

  constructor(private stompService: StompRService) {
    this.init();
  }

  ngOnDestroy(): void {
    this.$ondestroy.next(1);
  }

  subscribe(vehicleId: string) {
    const sub = this.stompService
      .subscribe(`/topic/${vehicleId}`)
      .pipe(takeUntil(this.$ondestroy))
      .subscribe(({ body }) => this.event.next(JSON.parse(body)));
    this.subs[vehicleId] = sub;
  }

  unsubscribe(vehicleId: string) {
    const sub = this.subs[vehicleId];
    if (!sub) return;
    sub.unsubscribe();
    delete this.subs[vehicleId];
  }

  private init(): void {
    if (!this.stompService.connected()) {
      this.stompService.config = this.stompConfig();
      this.stompService.initAndConnect();
    }
  }

  private stompConfig(): StompConfig {
    const provider = function () {
      return new SockJS('/websocket');
    };

    const config = new StompConfig();
    config.url = provider;
    config.heartbeat_in = 0;
    config.heartbeat_out = 0;
    config.reconnect_delay = 10000;

    return config;
  }
}
