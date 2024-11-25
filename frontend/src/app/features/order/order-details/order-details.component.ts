import { Component, EventEmitter, Input, OnInit, Output } from "@angular/core";

@Component({
    selector: 'order-details',
    templateUrl: './order-details.component.html',
})
export default class OrderDetails {
    @Input() isOpen = false; 
    @Output() onClose = new EventEmitter<void>();

    closeModal() {
        this.isOpen = false;
        this.onClose.emit();
    }
}