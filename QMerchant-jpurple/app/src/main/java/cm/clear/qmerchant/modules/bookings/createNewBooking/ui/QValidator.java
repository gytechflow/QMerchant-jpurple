package cm.clear.qmerchant.modules.bookings.createNewBooking.ui;

import android.view.View;

public abstract class QValidator {
    protected View view;

    protected QValidator() {

    }

    protected QValidator copy(View v){
        QValidator copyValidator = new QValidator() {
            @Override
            boolean validate(String s) {
                return QValidator.this.validate(s);
            }

            @Override
            void showError(boolean b) {
                QValidator.this.showError(b);
            }

            @Override
            public View getView() {
                return v;
            }
        };

        return copyValidator;
    }

    abstract boolean validate(String s);

    abstract void showError(boolean b);

    public QValidator setView(View view) {
        this.view = view;
        return this;
    }

    public View getView() {
        return view;
    }
}
