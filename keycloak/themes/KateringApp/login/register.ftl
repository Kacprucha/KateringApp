<#import "template.ftl" as layout>

<script>
    function toggleRegistrationType() {
        var clientFields = document.getElementById("client-fields");
        var firmFields = document.getElementById("firm-fields");

        if (document.getElementById("register-client").checked) {
            clientFields.style.display = "block";
            firmFields.style.display = "none";
        } else {
            clientFields.style.display = "none";
            firmFields.style.display = "block";
        }
    }
</script>

<@layout.registrationLayout displayInfo=social.displayInfo; section>
    <#if section = "pageTitle">
        ${msg("signup")}
    <#elseif section = "form">
        <div class="divider"></div>
        <div class="kcform">
            <h1 id="kc-page-title" style="text-align: center;">
                ${msg("Sign up")}
            </h1>

            <div class="register-switch" style="text-align: right">
                <label><input type="radio" name="register-type" id="register-client" onclick="toggleRegistrationType()" checked> Client</label>
                <label><input type="radio" name="register-type" id="register-firm" onclick="toggleRegistrationType()"> Firm</label>
            </div>

            <div id="client-fields" style="display: block;">

                <form action="${url.registrationAction}" method="post">

                    <div class="${properties.kcFormGroupClass!} ${messagesPerField.printIfExists('email',properties.kcFormGroupErrorClass!)}">
                        <div class="${properties.kcLabelWrapperClass!}">
                            <label for="email">${msg("Email")}</label>
                        </div>
                        <div class="${properties.kcInputWrapperClass!}">
                            <input type="email" id="email" class="${properties.kcInputClass!}" name="email" value="${(register.formData.email!'')}" autocomplete="email" required/>
                        </div>
                    </div>

                    <div class="${properties.kcFormGroupClass!} ${messagesPerField.printIfExists('username',properties.kcFormGroupErrorClass!)}">
                        <div class="${properties.kcLabelWrapperClass!}">
                            <label for="username">${msg("Username")}</label>
                        </div>
                        <div class="${properties.kcInputWrapperClass!}">
                            <input type="text" id="username" class="${properties.kcInputClass!}" name="username" value="${(register.formData.username!'')}" autocomplete="username" pattern=".*" title="${msg("usernameValid")}" required/>
                        </div>
                    </div>

                    <#if passwordRequired??>
                    <div class="${properties.kcFormGroupClass!} ${messagesPerField.printIfExists('password',properties.kcFormGroupErrorClass!)}">
                        <div class="${properties.kcLabelWrapperClass!}">
                            <label for="password">${msg("Password")}</label>
                        </div>
                        <div class="${properties.kcInputWrapperClass!}">
                            <input type="password" id="password" pattern=".*"  class="${properties.kcInputClass!}" name="password" autocomplete="new-password" required/>
                        </div>
                    </div>

                    <div class="${properties.kcFormGroupClass!} ${messagesPerField.printIfExists('password-confirm',properties.kcFormGroupErrorClass!)}">
                        <div class="${properties.kcLabelWrapperClass!}">
                            <label for="password-confirm">${msg("Confirm Password")}</label>
                        </div>
                        <div class="${properties.kcInputWrapperClass!}">
                            <input type="password" id="password-confirm" class="${properties.kcInputClass!}" name="password-confirm" autocomplete="new-password" required/>
                        </div>
                    </div>
                    </#if>


                    <div class="${properties.kcFormGroupClass!}"">
                        <div class="form-check">
                            <input type="checkbox" required class="form-check-input" id="user.attributes.terms" name="user.attributes.terms"/>
                            <label for="user.attributes.terms" class="form-check-label">${msg("terms")?no_esc}</label>
                        </div>
                    </div>

                    <div class="${properties.kcFormGroupClass!}">
                        <div class="form-buttons">
                            <input class="${properties.kcButtonClass!} ${properties.kcButtonPrimaryClass!} ${properties.kcButtonBlockClass!} ${properties.kcButtonLargeClass!}" type="submit" value="${msg("signup")}"/>
                        </div>
                    </div>
                </form>

            </div>

            <div id="firm-fields" style="display: none;">

                <form action="${url.registrationAction}" method="post">

                    <div class="${properties.kcFormGroupClass!} ${messagesPerField.printIfExists('username',properties.kcFormGroupErrorClass!)}">
                        <div class="${properties.kcLabelWrapperClass!}">
                            <label for="username">${msg("Frim name")}</label>
                        </div>
                        <div class="${properties.kcInputWrapperClass!}">
                            <input type="text" id="username" class="${properties.kcInputClass!}" name="username" value="${(register.formData.username!'')}" autocomplete="username" pattern=".*" title="${msg("usernameValid")}" required/>
                        </div>
                    </div>

                    <div class="${properties.kcFormGroupClass!} ${messagesPerField.printIfExists('username',properties.kcFormGroupErrorClass!)}">
                        <div class="${properties.kcLabelWrapperClass!}">
                            <label for="phoneNumber">${msg("Phone number")}</label>
                        </div>
                        <div class="${properties.kcInputWrapperClass!}">
                            <input type="text" id="phoneNumber" class="${properties.kcInputClass!}" name="phoneNumber" value="${(register.formData.phoneNumber!'')}" autocomplete="phoneNumber" pattern=".*" title="${msg("phoneValid")}" required/>
                        </div>
                    </div>

                    <div class="${properties.kcFormGroupClass!} ${messagesPerField.printIfExists('username',properties.kcFormGroupErrorClass!)}">
                        <div class="${properties.kcLabelWrapperClass!}">
                            <label for="address">${msg("Address")}</label>
                        </div>
                        <div class="${properties.kcInputWrapperClass!}">
                            <input type="text" id="address" class="${properties.kcInputClass!}" name="address" value="${(register.formData.address!'')}" autocomplete="address" pattern=".*" title="${msg("addressValid")}" required/>
                        </div>
                    </div>

                     <div class="${properties.kcFormGroupClass!} ${messagesPerField.printIfExists('email',properties.kcFormGroupErrorClass!)}">
                        <div class="${properties.kcLabelWrapperClass!}">
                            <label for="email">${msg("Email")}</label>
                        </div>
                        <div class="${properties.kcInputWrapperClass!}">
                            <input type="email" id="email" class="${properties.kcInputClass!}" name="email" value="${(register.formData.email!'')}" autocomplete="email" required/>
                        </div>
                    </div>

                    <#if passwordRequired??>
                    <div class="${properties.kcFormGroupClass!} ${messagesPerField.printIfExists('password',properties.kcFormGroupErrorClass!)}">
                        <div class="${properties.kcLabelWrapperClass!}">
                            <label for="password">${msg("Password")}</label>
                        </div>
                        <div class="${properties.kcInputWrapperClass!}">
                            <input type="password" id="password" pattern=".*"  class="${properties.kcInputClass!}" name="password" autocomplete="new-password" required/>
                        </div>
                    </div>

                    <div class="${properties.kcFormGroupClass!} ${messagesPerField.printIfExists('password-confirm',properties.kcFormGroupErrorClass!)}">
                        <div class="${properties.kcLabelWrapperClass!}">
                            <label for="password-confirm">${msg("Confirm Password")}</label>
                        </div>
                        <div class="${properties.kcInputWrapperClass!}">
                            <input type="password" id="password-confirm" class="${properties.kcInputClass!}" name="password-confirm" autocomplete="new-password" required/>
                        </div>
                    </div>
                    </#if>

                <div class="${properties.kcFormGroupClass!}"">
                    <div class="form-check">
                        <input type="checkbox" required class="form-check-input" id="user.attributes.terms" name="user.attributes.terms"/>
                        <label for="user.attributes.terms" class="form-check-label">${msg("terms")?no_esc}</label>
                    </div>
                </div>

                <div class="${properties.kcFormGroupClass!}">
                    <div class="form-buttons">
                        <input class="${properties.kcButtonClass!} ${properties.kcButtonPrimaryClass!} ${properties.kcButtonBlockClass!} ${properties.kcButtonLargeClass!}" type="submit" value="${msg("signup")}"/>
                    </div>
                </div>
            </form>
        </div>
    </div>
   </#if>
</@layout.registrationLayout>