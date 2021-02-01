import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { PrimeraComponent } from './components/primera/primera.component';
import { SegundaComponent } from './components/segunda/segunda.component';

const routes: Routes = [
  {path: '', pathMatch: 'full', redirectTo: 'primera'},
  {path: 'primera', component: PrimeraComponent},
  {path: 'segunda', component: SegundaComponent},

];

@NgModule({
  imports: [RouterModule.forRoot(routes, { useHash: true })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
