import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AbsenceListComponent } from './absence-list.component';

describe('AbsenceListComponent', () => {
  let component: AbsenceListComponent;
  let fixture: ComponentFixture<AbsenceListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AbsenceListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AbsenceListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
