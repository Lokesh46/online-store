import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ApiService } from '../../services/api.service';
import { CartItem } from '../../models/cart-item.model';

@Component({
  selector: 'app-cart',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './cart.html'
})
export class CartComponent implements OnInit {
  cart: CartItem[] = [];
  userId = 1;

  constructor(private api: ApiService) {}
  errorMessage: string | null = null;

  ngOnInit(): void {
    this.loadCart();
  }

  loadCart(): void {
    this.api.getCart().subscribe({
      next: (data) => (this.cart = data),
      error: (err) => console.error('Failed to load cart:', err)
    });
  }

  getTotal(): number {
    return this.cart.reduce((sum, item) => sum + item.unitPrice * item.quantity, 0);
  }

  increaseQuantity(item: CartItem) {
  const newQuantity = item.quantity + 1;

  this.api.updateCart(item.productId, newQuantity).subscribe({
    next: () => {
      item.quantity = newQuantity;
      this.errorMessage = null;
    },
    error: (err) => {
      console.error('Failed to update cart:', err);
      this.errorMessage = 'Cannot increase quantity beyond available stock.';
    }
  });
}


  decreaseQuantity(item: CartItem): void {
    if (item.quantity > 1) {
      const newQty = item.quantity - 1;
      this.api.updateCartQuantity(this.userId, item.productId, newQty).subscribe(() => {
        this.loadCart();
      });
    }
  }

  removeItem(item: CartItem): void {
    this.api.removeFromCart(item.productId, this.userId).subscribe(() => {
      this.loadCart();
    });
  }
}
