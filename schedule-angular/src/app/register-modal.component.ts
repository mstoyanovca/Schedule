import { Component } from '@angular/core';
import { MdDialog, MdDialogRef } from '@angular/material';

@Component( {
    selector: 'register',
    template: '<button class="btn btn-primary" (click)="openDialog()">Register</button>'
})

export class RegisterModalComponent {

    selectedOption: string;

    constructor( public dialog: MdDialog ) { }

    openDialog() {
        let dialogRef = this.dialog.open( RegisterModal, {
            // width: '600px',
            // height: '400px'
        } );
        dialogRef.afterClosed().subscribe( result => {
            this.selectedOption = result;
        });
    }
}

@Component( {
    templateUrl: './register-modal.component.html'
})

export class RegisterModal {
    constructor( public dialogRef: MdDialogRef<RegisterModal> ) { }
}














