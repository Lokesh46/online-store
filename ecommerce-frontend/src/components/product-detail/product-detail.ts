import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { ApiService, Product } from '../../services/api.service';

@Component({
  selector: 'app-product-detail',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './product-detail.html'
})
export class ProductDetailComponent implements OnInit {
  productId!: number;
  product: Product | null = null;
  relatedProducts: Product[] = [];

  constructor(private route: ActivatedRoute, private api: ApiService) {}

  ngOnInit(): void {
  this.route.params.subscribe(params => {
    const id = +params['id'];
    this.productId = id;

    this.api.getProductById(id).subscribe({
      next: (product) => this.product = product,
      error: (err) => console.error('Error loading product:', err)
    });

    this.api.getRelatedProducts(id).subscribe({
      next: (data) => this.relatedProducts = data,
      error: (err) => console.error('Error loading related products:', err)
    });
  });
}


  loadProduct(): void {
    this.api.getProductById(this.productId).subscribe({
      next: (data) => this.product = data,
      error: (err) => console.error('Error loading product:', err)
    });
  }

  

  loadRelatedProducts(): void {
    this.api.getRelatedProducts(this.productId).subscribe({
      next: (data) => this.relatedProducts = data,
      error: (err) => console.error('Error loading related products:', err)
    });
  }

  addToCart(productId: number) {
  this.api.addToCart(productId, 1).subscribe({
    next: () => alert('Product added to cart!'),
    error: (err) => alert('Failed to add to cart: ' + err.message)
  });

}
}
