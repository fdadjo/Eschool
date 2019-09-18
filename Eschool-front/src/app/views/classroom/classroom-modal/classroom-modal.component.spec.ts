import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ClassroomModalComponent } from './classroom-modal.component';

describe('ClassroomModalComponent', () => {
  let component: ClassroomModalComponent;
  let fixture: ComponentFixture<ClassroomModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ClassroomModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ClassroomModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
