import { Component } from '@angular/core';
import { User } from './user';

@Component( {
    selector: 'app-root',
    templateUrl: './login-form.component.html',
    styleUrls: ['./login-form.component.css']
})

export class LoginFormComponent {

    model = new User( 0, '', '' );
    error = false;
    invalidToken = false;
    loggedOut = false;
    activated = false;
    activate = false;
    changePwd = false;
    emailNotReg = false;

    onSubmit() {

    }

    newUser() {
        this.model = new User( 0, '', '' );
    }
}