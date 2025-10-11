import { Product } from './product.model';

export interface CartItem {
  id: number;
  userId: number;
  productId: number;
  productName: string;
  quantity: number;
  unitPrice: number;
  totalPrice: number;
}
