import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { HomeworkDirectorComponent } from './homework-director.component';

describe('HomeworkDirectorComponent', () => {
  let component: HomeworkDirectorComponent;
  let fixture: ComponentFixture<HomeworkDirectorComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ HomeworkDirectorComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HomeworkDirectorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
