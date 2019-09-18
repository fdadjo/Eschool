import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { HomeworkModalComponent } from './homework-modal.component';

describe('HomeworkModalComponent', () => {
  let component: HomeworkModalComponent;
  let fixture: ComponentFixture<HomeworkModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ HomeworkModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HomeworkModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
