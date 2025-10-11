import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute,Router } from '@angular/router';
import { ApiService, Product } from '../../services/api.service';

@Component({
  selector: 'app-product-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './product-list.html' 
})


export class ProductListComponent implements OnInit {
  products: Product[] = [];
  categoryId!: number;

  constructor(private api: ApiService, private router: Router, private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.categoryId = +params['id'];
      this.loadProducts();
    });
  }

  loadProducts() {
    this.api.getProductsByCategory(this.categoryId).subscribe({
      next: (data) => this.products = data,
      error: (err) => console.error('Error loading products:', err)
    });
  }

  addToCart(productId: number) {
  this.api.addToCart(productId, 1).subscribe({
    next: () => alert('Product added to cart!'),
    error: (err) => alert('Failed to add to cart: ' + err.message)
  });
}

goToProduct(id: number): void {
  this.router.navigate(['/product', id]);
}


}
