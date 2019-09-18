import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ComposeComponent } from './compose/compose.component';
import { InboxComponent } from './inbox/inbox.component';
import { MessageComponent } from './message/message.component';

@NgModule({
  imports: [
    CommonModule
  ],
  declarations: [ComposeComponent, InboxComponent, MessageComponent]
})
export class EmailModule { }
