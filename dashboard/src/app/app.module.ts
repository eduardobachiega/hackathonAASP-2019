import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppRoutingModule } from "./app.routing.module";

import { AppComponent } from './app.component';
import { GraphComponent } from './views/graph/graph.component';
import { ForumComponent } from './views/forum/forum.component';

@NgModule({
  declarations: [
    AppComponent,
    GraphComponent,
    ForumComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
