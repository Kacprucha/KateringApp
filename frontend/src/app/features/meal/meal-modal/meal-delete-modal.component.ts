import { Component, EventEmitter, Input, OnInit, Output } from "@angular/core";

@Component({
    selector: 'meal-modal',
    templateUrl: './meal-delete-modal.component.html',
})
export default class MealDeleteModal {
    @Input() isOpen = false; 
    @Output() onClose = new EventEmitter<void>();

    closeModal() {
        this.isOpen = false;
        this.onClose.emit();
    }
}