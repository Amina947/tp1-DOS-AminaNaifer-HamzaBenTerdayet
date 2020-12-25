import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IOrdonnance } from 'app/shared/model/ordonnance.model';
import { OrdonnanceService } from './ordonnance.service';
import { OrdonnanceDeleteDialogComponent } from './ordonnance-delete-dialog.component';

@Component({
  selector: 'jhi-ordonnance',
  templateUrl: './ordonnance.component.html',
})
export class OrdonnanceComponent implements OnInit, OnDestroy {
  ordonnances?: IOrdonnance[];
  eventSubscriber?: Subscription;

  constructor(protected ordonnanceService: OrdonnanceService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.ordonnanceService.query().subscribe((res: HttpResponse<IOrdonnance[]>) => (this.ordonnances = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInOrdonnances();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IOrdonnance): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInOrdonnances(): void {
    this.eventSubscriber = this.eventManager.subscribe('ordonnanceListModification', () => this.loadAll());
  }

  delete(ordonnance: IOrdonnance): void {
    const modalRef = this.modalService.open(OrdonnanceDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.ordonnance = ordonnance;
  }
}
