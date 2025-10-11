import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Category } from '../../../services/api.service';

@Component({
  selector: 'app-category-subtree',
  standalone: true,
  imports: [CommonModule],
  template: `
    <ul>
      <li *ngFor="let node of nodes">
        <a (click)="select(node.id)" style="cursor: pointer;">
          {{ node.name }}
        </a>
        <app-category-subtree
          *ngIf="node.children?.length"
          [nodes]="node.children ?? []"
          (selectCategory)="select($event)">
        </app-category-subtree>
      </li>
    </ul>
  `
})
export class CategorySubtreeComponent {
  @Input() nodes: Category[] = [];
  @Output() selectCategory = new EventEmitter<number>();

  select(id: number) {
    this.selectCategory.emit(id);
  }
}
