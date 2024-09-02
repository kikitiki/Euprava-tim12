import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CekajuKarticuComponent } from './cekaju-karticu.component';

describe('CekajuKarticuComponent', () => {
  let component: CekajuKarticuComponent;
  let fixture: ComponentFixture<CekajuKarticuComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CekajuKarticuComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CekajuKarticuComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
