import { Routes } from '@angular/router';

export const appRoutes: Routes = [
  {
    path: '',
    loadComponent: () =>
      import('../components/category-tree/category-tree').then(m => m.CategoryTreeComponent)
  },
  {
    path: 'category/:id',
    loadComponent: () =>
      import('../components/product-list/product-list').then(m => m.ProductListComponent)
  },
  {
    path: 'cart',
    loadComponent: () =>
      import('../components/cart/cart').then(m => m.CartComponent)
  },
  {
    path: 'product/:id',
    loadComponent: () =>
      import('../components/product-detail/product-detail').then(m => m.ProductDetailComponent)
  },
  {
    path: '**',
    redirectTo: ''
  }
];
