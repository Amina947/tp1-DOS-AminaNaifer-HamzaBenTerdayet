import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'medecin',
        loadChildren: () => import('./medecin/medecin.module').then(m => m.ProjectJhipsterrrMedecinModule),
      },
      {
        path: 'patient',
        loadChildren: () => import('./patient/patient.module').then(m => m.ProjectJhipsterrrPatientModule),
      },
      {
        path: 'ordonnance',
        loadChildren: () => import('./ordonnance/ordonnance.module').then(m => m.ProjectJhipsterrrOrdonnanceModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class ProjectJhipsterrrEntityModule {}
