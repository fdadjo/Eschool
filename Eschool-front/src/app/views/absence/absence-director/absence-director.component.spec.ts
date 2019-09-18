import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AbsenceDirectorComponent } from './absence-director.component';

describe('AbsenceDirectorComponent', () => {
  let component: AbsenceDirectorComponent;
  let fixture: ComponentFixture<AbsenceDirectorComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AbsenceDirectorComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AbsenceDirectorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
