import { Injectable, inject } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { CartItem } from '../models/cart-item.model';
import { ApiService } from './api.service';

@Injectable({
  providedIn: 'root'
})
export class CartService {
  private api = inject(ApiService);
  private _items = new BehaviorSubject<CartItem[]>([]);
  readonly items$: Observable<CartItem[]> = this._items.asObservable();

  loadCart() {
    this.api.getCart().subscribe(items => {
      this._items.next(items);
    });
  }

  addProduct(productId: number, qty: number) {
    this.api.addToCart(productId, qty).subscribe(() => {
      this.loadCart();
    });
  }

}
