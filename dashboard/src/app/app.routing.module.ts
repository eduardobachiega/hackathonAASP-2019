import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";

/* Views */
import { GraphComponent } from "./views/graph/graph.component";
import { ForumComponent } from "./views/forum/forum.component";

const routes: Routes = [
  { path: "", redirectTo: "/graph", pathMatch: "full" },
  { path: "graph", component: GraphComponent },
  { path: "forum", component: ForumComponent}
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes, {
      useHash: true,
      onSameUrlNavigation: "reload"
    })
  ],
  exports: [RouterModule]
})
export class AppRoutingModule {}