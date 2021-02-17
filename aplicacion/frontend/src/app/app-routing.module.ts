import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PrimeraComponent } from './components/primera/primera.component';
import { SegundaComponent } from './components/segunda/segunda.component';
import { SignOutComponent } from './sign-out/sign-out.component';

const routes: Routes = [
  {path: '', pathMatch: 'full', redirectTo: 'primera'},
  {path: 'primera', component: PrimeraComponent},
  {path: 'segunda', component: SegundaComponent},
  {
      path: 'sign-out',
      component: SignOutComponent
  },
  {
      path: '**',
      redirectTo: 'demo-angular',
      pathMatch: 'full'
  }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
