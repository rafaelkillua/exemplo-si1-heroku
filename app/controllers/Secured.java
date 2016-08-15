package controllers;

import play.mvc.*;
import play.mvc.Http.*;

import static play.mvc.Controller.flash;


public class Secured extends Security.Authenticator {

    @Override
    public String getUsername(Context ctx) {
        return ctx.session().get("login");
    }

    @Override
    public Result onUnauthorized(Context ctx) {
        flash("erro", "Você não está logado no sistema.");
        return redirect(routes.Application.login());
    }
}
