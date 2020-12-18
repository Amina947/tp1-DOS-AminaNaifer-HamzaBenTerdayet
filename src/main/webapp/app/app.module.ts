import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { ProjectJhipsterrrSharedModule } from 'app/shared/shared.module';
import { ProjectJhipsterrrCoreModule } from 'app/core/core.module';
import { ProjectJhipsterrrAppRoutingModule } from './app-routing.module';
import { ProjectJhipsterrrHomeModule } from './home/home.module';
import { ProjectJhipsterrrEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    ProjectJhipsterrrSharedModule,
    ProjectJhipsterrrCoreModule,
    ProjectJhipsterrrHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    ProjectJhipsterrrEntityModule,
    ProjectJhipsterrrAppRoutingModule,
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [MainComponent],
})
export class ProjectJhipsterrrAppModule {}
