import { MATCHES } from './../../testing/match-stubs';
import { Player } from './../../players/shared/player.model';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { FormsModule } from '@angular/forms';
import { ActivatedRouteStub } from './../../testing/routing-stubs';
import { ActivatedRoute } from '@angular/router';
import { PlayerServiceStub, PLAYERS } from './../../testing/player-stubs';
import { PlayerService } from './../../players/shared/player.service';
import { QueueServiceStub, QUEUE } from './../../testing/queue-stubs';
import { HttpModule } from '@angular/http';
import { AppModule } from './../../app.module';
/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { QueueAddComponent } from './../queue-add/queue-add.component';
import { QueueListComponent } from './queue-list.component';

describe('QueueComponent', () => {
  let component: QueueListComponent;
  let fixture: ComponentFixture<QueueListComponent>;
  let componentAdd: QueueAddComponent;
  let fixtureAdd: ComponentFixture<QueueAddComponent>;

  let playerService: PlayerService;
  let activatedRoute: ActivatedRoute;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        QueueListComponent,
        QueueAddComponent 
      ],
      imports: [ 
        FormsModule, 
        NgbModule.forRoot() 
      ], 
      providers: [
        { provide: PlayerService, useClass: PlayerServiceStub }
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(QueueListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should have queue', () => {
    component.queue = QUEUE;
    fixture.detectChanges();
    expect(component).toBeTruthy();
    let debugElement = fixture.debugElement.query(By.css('div#daily-queue h2'));
    expect(debugElement.nativeElement.textContent).toEqual('testQueue');
  });

  it('should add match to queue', fakeAsync(() => {
    component.queue = QUEUE;
    expect(component.queue.matches.length).toEqual(1);

    fixtureAdd = TestBed.createComponent(QueueAddComponent);
    componentAdd = fixtureAdd.componentInstance;
    let formButton = fixtureAdd.debugElement.query(By.css('div form div button'));
    expect(formButton.nativeElement.textContent).toEqual('Add match to queue');

    let playerOne = PLAYERS[0];
    let playerTwo = PLAYERS[1];
    let match = MATCHES[0];
    match.playerOne = playerOne;
    match.playerTwo = playerTwo;
    componentAdd.match = match;
    // TODO: Test adding match to queue
    // formButton.triggerEventHandler('click', null);
    // fixture.detectChanges();
    // tick();
    // expect(component.queue.matches.length).toEqual(2);
  }));
});