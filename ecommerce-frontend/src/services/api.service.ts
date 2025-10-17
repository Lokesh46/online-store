import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CartItem } from '../models/cart-item.model';

export interface Category {
  id: number;
  name: string;
  parentId?: number | null;
  children?: Category[];
}

export interface Product {
  id: number;
  name: string;
  price: number;
  availabilityQty: number;
  categoryId: number;
}



@Injectable({
  providedIn: 'root'
})
export class ApiService {
  private baseUrl = 'https://backend-0g1z.onrender.com';

  constructor(private http: HttpClient) {}

  getCategories(): Observable<Category[]> {
    return this.http.get<Category[]>(`${this.baseUrl}/categories`);
  }

  getProducts(categoryId: number): Observable<Product[]> {
    const params = new HttpParams().set('categoryId', categoryId);
    return this.http.get<Product[]>(`${this.baseUrl}/products`, { params });
  }

  getProductById(productId: number): Observable<Product> {
  return this.http.get<Product>(`${this.baseUrl}/products/${productId}`);
}


  addToCart(productId: number, quantity: number): Observable<any> {
    return this.http.post(`${this.baseUrl}/cart/add`, { productId, quantity });
  }

  getCart(): Observable<CartItem[]> {
    return this.http.get<CartItem[]>(`${this.baseUrl}/cart`);
  }

  getRelatedProducts(productId: number): Observable<Product[]> {
    return this.http.get<Product[]>(`${this.baseUrl}/products/${productId}/related`);
  }

  getProductsByCategory(categoryId: number): Observable<Product[]> {
    return this.http.get<Product[]>(`${this.baseUrl}/products?categoryId=${categoryId}`);
  }


updateCartQuantity(userId: number, productId: number, quantity: number): Observable<void> {
  return this.http.post<void>(`${this.baseUrl}/cart/update`, { userId, productId, quantity });
}

updateCart(productId: number, quantity: number, userId: number = 1): Observable<any> {
  const body = {
    productId,
    quantity,
    userId
  };
  return this.http.post(`${this.baseUrl}/cart/update`, body);
}


removeFromCart(productId: number, userId: number = 1): Observable<void> {
  const params = new HttpParams()
    .set('productId', productId.toString())
    .set('userId', userId.toString());

  return this.http.delete<void>(`${this.baseUrl}/cart/remove`, { params });
}




}
