import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { ApiService, Category } from '../../services/api.service';
import { CategorySubtreeComponent } from './category-subtree/category-subtree';
@Component({
  selector: 'app-category-tree',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './category-tree.html',
  styleUrls: ['./category-tree.css']
})
export class CategoryTreeComponent implements OnInit {
  categories: Category[] = [];

  constructor(private api: ApiService, private router: Router) {}

  ngOnInit(): void {
    this.api.getCategories().subscribe({
      next: (data) => (this.categories = data),
      error: (err) => console.error('Error loading categories:', err)
    });
  }

  goToCategory(id: number): void {
    this.router.navigate(['/category', id]);
  }

  getColor(id: number): string {
    const colors = ['#0d6efd', '#6610f2', '#6f42c1', '#d63384', '#dc3545', '#fd7e14', '#ffc107', '#198754', '#20c997'];
    return colors[id % colors.length];
  }

  getColorByLevel(level: number): string {
  const levelColors = [
    '#0d6efd', // level 0 - blue
    '#6610f2', // level 1 - purple
    '#fd7e14', // level 2 - orange
    '#20c997', // level 3 - teal
    '#dc3545', // level 4 - red
    '#6f42c1', // level 5 - indigo
    '#198754'  // level 6 - green
  ];
  return levelColors[level % levelColors.length]; 
}

}
