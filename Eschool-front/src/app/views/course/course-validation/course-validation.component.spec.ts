import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CourseValidationComponent } from './course-validation.component';

describe('CourseValidationComponent', () => {
  let component: CourseValidationComponent;
  let fixture: ComponentFixture<CourseValidationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CourseValidationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CourseValidationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
