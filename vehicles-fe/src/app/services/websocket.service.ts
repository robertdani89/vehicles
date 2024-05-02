import { Injectable, OnDestroy } from '@angular/core';
import { StompRService, StompConfig } from '@stomp/ng2-stompjs';
import SockJS from 'sockjs-client';
import { merge, of, Subject, Subscription, switchMap, takeUntil } from 'rxjs';

export enum MessageType {
  position = 'position',
  notification = 'notification',
}

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
    const position = this.stompService
      .subscribe(`/topic/vehicles/${vehicleId}/pos`)
      .pipe(
        takeUntil(this.$ondestroy),
        switchMap(({ body }) =>
          of({
            type: MessageType.position,
            id: vehicleId,
            data: JSON.parse(body),
          })
        )
      );

    const notification = this.stompService
      .subscribe(`/topic/vehicles/${vehicleId}/not`)
      .pipe(
        takeUntil(this.$ondestroy),
        switchMap(({ body }) =>
          of({ type: MessageType.notification, id: vehicleId, data: body })
        )
      );

    const sub = merge(position, notification)
      .pipe(takeUntil(this.$ondestroy))
      .subscribe((event) => {
        this.event.next(event);
      });

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
