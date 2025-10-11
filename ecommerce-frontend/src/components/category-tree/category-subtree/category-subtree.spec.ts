import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CategorySubtree } from './category-subtree';

describe('CategorySubtree', () => {
  let component: CategorySubtree;
  let fixture: ComponentFixture<CategorySubtree>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CategorySubtree]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CategorySubtree);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
