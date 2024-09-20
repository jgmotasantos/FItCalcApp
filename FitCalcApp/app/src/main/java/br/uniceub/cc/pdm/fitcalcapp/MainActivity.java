package br.uniceub.cc.pdm.fitcalcapp;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    float x1;
    float x2;

    // Tela Main (Ao abrir o APP)
    public Button buttonCalcularIMC;
    public Button buttonCalcularPesoIdeal;
    public Button buttonCalcularAlturaIdeal;
    public LinearLayout mainpage;

    // Tela Calcular IMC
    public EditText Peso;
    public EditText Altura;
    public Button calcularIMCPage;
    public Button voltarIMCPage;
    public LinearLayout calc_imc;
    public RadioGroup imc_options;

    // Tela Calcular Peso Ideal
    public EditText AlturaTelaPeso;
    public Button calcularPesoIdealTelaPeso;
    public Button voltarPesoPage;
    public LinearLayout calc_peso_layout;
    public RadioGroup peso_options;

    // Tela Calcular Altura Ideal
    public EditText TextIMCTelaAltura;
    public Button calculaAlturaIdealTelaAltura;
    public Button voltarALturaPage;
    public LinearLayout calc_altura;
    public RadioGroup altura_options;
    public

    @Override void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.tela_principal);
        CarregarTelaPrincipal();
    }

    public void CarregarTelaPrincipal() {
        setContentView(R.layout.tela_principal);
        buttonCalcularIMC = findViewById(R.id.buttonCalcularIMC);
        buttonCalcularIMC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CarregarCalculadoraIMC();
            }
        });

        buttonCalcularPesoIdeal = findViewById(R.id.buttonCalcularPesoIdeal);
        buttonCalcularPesoIdeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CarregarCalculadoraPesoIdeal();
            }
        });

        buttonCalcularAlturaIdeal = findViewById(R.id.buttonCalcularAlturaIdeal);
        buttonCalcularAlturaIdeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CarregarCalculadoraAlturaIdeal();
            }
        });
    }

    public void CarregarCalculadoraIMC() {
        setContentView(R.layout.calculadora_imc);

        voltarIMCPage = findViewById(R.id.buttonVoltarIMCPage);
        Peso = findViewById(R.id.TextPeso);
        Altura = findViewById(R.id.TextAltura);
        calcularIMCPage = findViewById(R.id.buttonCalcularIMCPage);
        imc_options = findViewById(R.id.imcOptions);

        voltarIMCPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CarregarTelaPrincipal();
            }
        });

        Peso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Peso.setText("");
            }
        });

        Altura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Altura.setText("");
            }
        });

        calcularIMCPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calcularIMC();
            }
        });

        calc_imc = findViewById(R.id.calc_imc);
        calc_imc.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        x1 = event.getX();
                        return true;
                    case MotionEvent.ACTION_UP:
                        x2 = event.getX();

                        if (x2 - x1 > 100) { // Swipe para a direita
                            Animation slideOutLeft = AnimationUtils.loadAnimation(MainActivity.this, R.anim.slide_out_left);
                            Animation slideInRight = AnimationUtils.loadAnimation(MainActivity.this, R.anim.slide_in_right);

                            calc_imc.startAnimation(slideOutLeft);

                            new android.os.Handler().postDelayed(() -> {
                                CarregarCalculadoraPesoIdeal();
                                LinearLayout calc_peso_layout = findViewById(R.id.calc_peso_layout); // Atualize a referência
                                calc_peso_layout.startAnimation(slideInRight);
                            }, slideOutLeft.getDuration()); // Ajuste o delay conforme necessário
                        }
                        if (x1 - x2 > 100) { // Swipe para a esquerda
                            Animation slideOutLeft = AnimationUtils.loadAnimation(MainActivity.this, R.anim.slide_out_left);
                            Animation slideInRight = AnimationUtils.loadAnimation(MainActivity.this, R.anim.slide_in_right);

                            calc_imc.startAnimation(slideOutLeft);

                            new android.os.Handler().postDelayed(() -> {
                                CarregarCalculadoraPesoIdeal();
                                LinearLayout calc_peso = findViewById(R.id.calc_peso_layout); // Atualize a referência
                                calc_peso.startAnimation(slideInRight);
                            }, slideOutLeft.getDuration()); // Ajuste o delay conforme necessário
                        }
                        return true;
                    default:
                        return false;
                }
            }
        });
    }



    private void calcularIMC() {
        String pesoStr = Peso.getText().toString();
        String alturaStr = Altura.getText().toString();

        if (pesoStr.isEmpty() || alturaStr.isEmpty()) {
            Toast.makeText(this, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        float peso = Float.parseFloat(pesoStr);
        float altura = Float.parseFloat(alturaStr);

        int selectedId = imc_options.getCheckedRadioButtonId();
        boolean isMasculino = (selectedId == R.id.radioButtonHomem);

        float imc = peso / (altura * altura);
        String resultado;

        if (isMasculino) {
            if (imc < 18.5) {
                resultado = "Abaixo do peso";
            } else if (imc >= 18.5 && imc < 24.9) {
                resultado = "Peso normal";
            } else if (imc >= 25.0 && imc < 29.9) {
                resultado = "Pré-obesidade";
            } else if (imc >= 30.0 && imc < 34.9) {
                resultado = "Obesidade Grau 1";
            } else if (imc >= 35.0 && imc < 39.9) {
                resultado = "Obesidade Grau 2";
            } else {
                resultado = "Obesidade Grau 3";
            }
        } else {
            if (imc < 18.5) {
                resultado = "Abaixo do peso";
            } else if (imc >= 18.5 && imc < 26.9) {
                resultado = "Peso normal";
            } else if (imc >= 27.0 && imc < 32.9) {
                resultado = "Pré-obesidade";
            } else if (imc >= 33.0 && imc < 37.9) {
                resultado = "Obesidade Grau 1";
            } else if (imc >= 38.0 && imc < 44.9) {
                resultado = "Obesidade Grau 2";
            } else {
                resultado = "Obesidade Grau 3";
            }
        }

        Toast.makeText(this, "Seu IMC: " + String.format("%.2f", imc) + " - " + resultado, Toast.LENGTH_LONG).show();
    }

    public void CarregarCalculadoraPesoIdeal() {
        setContentView(R.layout.calculadora_peso_ideal);

        voltarPesoPage = findViewById(R.id.buttonVoltarPesoPage);
        AlturaTelaPeso = findViewById(R.id.TextAlturaTelaPeso);
        calcularPesoIdealTelaPeso = findViewById(R.id.buttonCalcularPesoPage);
        peso_options = findViewById(R.id.pesoOptions);

        voltarPesoPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CarregarTelaPrincipal();
            }
        });

        AlturaTelaPeso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlturaTelaPeso.setText("");
            }
        });

        calcularPesoIdealTelaPeso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String alturaStr = AlturaTelaPeso.getText().toString();

                if (alturaStr.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Por favor, preencha o campo de altura.", Toast.LENGTH_SHORT).show();
                    return;
                }

                float altura = Float.parseFloat(alturaStr);
                int selectedId = peso_options.getCheckedRadioButtonId();
                boolean isMasculino = (selectedId == R.id.radioButtonHomem);

                float imcIdeal;
                if (isMasculino) {
                    imcIdeal = 21.7f;
                } else {
                    imcIdeal = 22.7f;
                }

                float pesoIdeal = imcIdeal * (altura * altura);

                Toast.makeText(MainActivity.this, "Seu peso ideal é: " + String.format("%.2f", pesoIdeal) + " kg", Toast.LENGTH_LONG).show();
            }
        });

        calc_peso_layout = findViewById(R.id.calc_peso_layout);
        calc_peso_layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        x1 = event.getX();
                        return true;
                    case MotionEvent.ACTION_UP:
                        x2 = event.getX();

                        if (x2 - x1 > 100) { // Swipe para a direita
                            Animation slideOutLeft = AnimationUtils.loadAnimation(MainActivity.this, R.anim.slide_out_left);
                            Animation slideInRight = AnimationUtils.loadAnimation(MainActivity.this, R.anim.slide_in_right);

                            calc_peso_layout.startAnimation(slideOutLeft);

                            new android.os.Handler().postDelayed(() -> {
                                CarregarCalculadoraIMC();
                                LinearLayout calc_imc = findViewById(R.id.calc_imc); // Atualize a referência
                                calc_imc.startAnimation(slideInRight);
                            }, slideOutLeft.getDuration()); // Ajuste o delay conforme necessário
                        }
                        if (x1 - x2 > 100) { // Swipe para a esquerda
                            Animation slideOutLeft = AnimationUtils.loadAnimation(MainActivity.this, R.anim.slide_out_left);
                            Animation slideInRight = AnimationUtils.loadAnimation(MainActivity.this, R.anim.slide_in_right);

                            calc_peso_layout.startAnimation(slideOutLeft);

                            new android.os.Handler().postDelayed(() -> {
                                CarregarCalculadoraAlturaIdeal();
                                LinearLayout calc_altura = findViewById(R.id.calc_altura); // Atualize a referência
                                calc_altura.startAnimation(slideInRight);
                            }, slideOutLeft.getDuration()); // Ajuste o delay conforme necessário
                        }
                        return true;
                    default:
                        return false;
                }
            }
        });
    }



    public void CarregarCalculadoraAlturaIdeal() {
        setContentView(R.layout.calculadora_altura_ideal);

        // Iniciar a animação do gradiente em movimento
        LinearLayout layout = findViewById(R.id.calc_altura);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.trasnlate_anim);
        layout.startAnimation(animation);

        voltarALturaPage = findViewById(R.id.buttonVoltarAlturaPage);
        TextIMCTelaAltura = findViewById(R.id.TextPesoTelaAltura);
        calculaAlturaIdealTelaAltura = findViewById(R.id.buttonCalcularAlturaPage);
        altura_options = findViewById(R.id.alturaOptions);

        voltarALturaPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CarregarTelaPrincipal();
            }
        });

        TextIMCTelaAltura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextIMCTelaAltura.setText("");
            }
        });

        calculaAlturaIdealTelaAltura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pesoStr = TextIMCTelaAltura.getText().toString();

                if (pesoStr.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Ei, você esqueceu de me dizer o peso! Vamos lá, preencha!", Toast.LENGTH_SHORT).show();
                    return;
                }

                float peso = Float.parseFloat(pesoStr);
                int selectedId = altura_options.getCheckedRadioButtonId();
                boolean isMasculino = (selectedId == R.id.radioButtonHomemTelaAltura);

                float imcIdeal;
                if (isMasculino) {
                    imcIdeal = 21.7f;
                } else {
                    imcIdeal = 22.7f;
                }

                float alturaIdeal = (float) Math.sqrt(peso / imcIdeal);

                String resultado;
                if (alturaIdeal < 1.5) {
                    resultado = "Wow! Parece que sua altura ideal seria digna de um hobbit! 🧙‍♂️";
                } else if (alturaIdeal >= 1.5 && alturaIdeal < 1.8) {
                    resultado = "Olha só, sua altura ideal te coloca no meio da galera! Nada mal, hein? 😎";
                } else {
                    resultado = "Altura ideal de gigante detectada! 🦸‍♂️ Quase alcançando as nuvens!";
                }

                Toast.makeText(MainActivity.this, "Sua altura ideal seria: " + String.format("%.2f", alturaIdeal) + " metros. " + resultado, Toast.LENGTH_LONG).show();
            }
        });

        calc_altura = findViewById(R.id.calc_altura);
        calc_altura.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        x1 = event.getX();
                        return true;
                    case MotionEvent.ACTION_UP:
                        x2 = event.getX();

                        if (x2 - x1 > 100) { // Swipe para a direita
                            Animation slideOutLeft = AnimationUtils.loadAnimation(MainActivity.this, R.anim.slide_out_left);
                            Animation slideInRight = AnimationUtils.loadAnimation(MainActivity.this, R.anim.slide_in_right);

                            calc_altura.startAnimation(slideOutLeft);
                            calc_peso_layout.startAnimation(slideInRight);

                            CarregarCalculadoraPesoIdeal();
                        }
                        if (x1 - x2 > 100) { // Swipe para a esquerda
                            Animation slideOutLeft = AnimationUtils.loadAnimation(MainActivity.this, R.anim.slide_out_left);
                            Animation slideInRight = AnimationUtils.loadAnimation(MainActivity.this, R.anim.slide_in_right);

                            calc_altura.startAnimation(slideOutLeft);
                            calc_imc.startAnimation(slideInRight);

                            CarregarCalculadoraIMC();
                        }
                        return true;
                    default:
                        return false;
                }
            }
        });
    }


}
