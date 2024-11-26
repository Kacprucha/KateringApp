import { Component, EventEmitter, Input, OnInit, Output } from "@angular/core";

@Component({
    selector: 'meal-modal',
    templateUrl: './meal-modal.component.html',
})
export default class MealModal {
    @Input() isOpen = false; 
    @Output() onClose = new EventEmitter<void>();

    closeModal() {
        this.isOpen = false;
        this.onClose.emit();
    }
}