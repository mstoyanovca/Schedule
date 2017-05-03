import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterModule } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from '@angular/material';
import 'hammerjs';
import { AppComponent } from './app.component';
import { LoginFormComponent } from './login-form.component';
import { RegisterModalComponent } from './register-modal.component';
import { RegisterModal } from './register-modal.component';

@NgModule( {
    declarations: [
        AppComponent,
        LoginFormComponent,
        RegisterModalComponent,
        RegisterModal
    ],
    imports: [
        BrowserModule,
        BrowserAnimationsModule,
        FormsModule,
        ReactiveFormsModule,
        MaterialModule,
        // HttpModule,
        RouterModule.forRoot( [
            {
                path: '', component: LoginFormComponent,

            }
        ] )
    ],
    entryComponents: [
        RegisterModal
    ],
    providers: [],
    bootstrap: [AppComponent]
})

export class AppModule { }














