import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SchoolSingleComponent } from './school-single.component';

describe('SchoolSingleComponent', () => {
  let component: SchoolSingleComponent;
  let fixture: ComponentFixture<SchoolSingleComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SchoolSingleComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SchoolSingleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
