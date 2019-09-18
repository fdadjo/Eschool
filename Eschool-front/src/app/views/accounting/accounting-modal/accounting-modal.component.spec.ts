import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AccountingModalComponent } from './accounting-modal.component';

describe('AccountingModalComponent', () => {
  let component: AccountingModalComponent;
  let fixture: ComponentFixture<AccountingModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AccountingModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AccountingModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
