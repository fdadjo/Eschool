import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AccountingDirectorComponent } from './accounting-director.component';

describe('AccountingDirectorComponent', () => {
  let component: AccountingDirectorComponent;
  let fixture: ComponentFixture<AccountingDirectorComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AccountingDirectorComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AccountingDirectorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
