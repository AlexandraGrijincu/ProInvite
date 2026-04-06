const API_BASE_URL = 'http://localhost:8080/api';

// --- NAVIGARE ---
function toggleAuth(target) {
    document.getElementById('register-section').style.display = target === 'register' ? 'block' : 'none';
    document.getElementById('login-section').style.display = target === 'login' ? 'block' : 'none';
    document.getElementById('registerMessage').innerText = "";
    document.getElementById('loginMessage').innerText = "";
}

function showInvitationForm(userId) {
    document.getElementById('main-nav').style.display = 'flex';
    document.getElementById('register-section').style.display = 'none';
    document.getElementById('login-section').style.display = 'none';
    document.getElementById('invitation-section').style.display = 'block';
    document.getElementById('gen_userId').value = userId;
}

function showSection(section) {
    document.getElementById('invitation-section').style.display = section === 'invitation' ? 'block' : 'none';
    document.getElementById('templates-section').style.display = section === 'gallery' ? 'block' : 'none';
    document.getElementById('nav-form').className = section === 'invitation' ? 'active' : '';
    document.getElementById('nav-templates').className = section === 'gallery' ? 'active' : '';
}

// --- ÎNREGISTRARE ---
document.getElementById('registerForm').addEventListener('submit', async (e) => {
    e.preventDefault();
    const data = {
        username: document.getElementById('reg_username').value,
        email: document.getElementById('reg_email').value,
        password: document.getElementById('reg_password').value
    };

    try {
        const res = await fetch(`${API_BASE_URL}/auth/register`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(data)
        });

        if (res.status === 201) {
            toggleAuth('login'); // Te trimite la login fara alerta sus
            document.getElementById('loginMessage').innerText = "Cont creat! Te poți loga.";
            document.getElementById('loginMessage').style.color = "#4BB543";
        } else {
            const err = await res.text();
            document.getElementById('registerMessage').innerText = err;
            document.getElementById('registerMessage').style.color = "#ff6b6b";
        }
    } catch (e) { alert("Eroare server backend!"); }
});

// --- LOGIN ---
document.getElementById('loginForm').addEventListener('submit', async (e) => {
    e.preventDefault();
    const data = {
        username: document.getElementById('login_username').value,
        password: document.getElementById('login_password').value
    };

    try {
        const res = await fetch(`${API_BASE_URL}/auth/login`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(data)
        });
        const msg = await res.text();
        if (res.status === 200) {
            const match = msg.match(/User ID\s*:\s*(\d+)/i);
            if (match) showInvitationForm(match[1]);
        } else {
            document.getElementById('loginMessage').innerText = msg;
            document.getElementById('loginMessage').style.color = "#ff6b6b";
        }
    } catch (e) { alert("Server offline!"); }
});

// --- GENERARE INVITATIE ---
document.getElementById('generateForm').addEventListener('submit', async (e) => {
    e.preventDefault();

    const messageElement = document.getElementById('generateMessage');
    messageElement.innerText = "Se procesează invitația...";
    messageElement.style.color = "white";

    const requestData = {
        userId: parseInt(document.getElementById('gen_userId').value),
        templateId: parseInt(document.getElementById('gen_templateId').value),
        eventTitle: document.getElementById('gen_title').value,
        eventDate: document.getElementById('gen_date').value,
        eventTime: document.getElementById('gen_time').value,
        eventLocation: document.getElementById('gen_location').value,
        eventDescription: document.getElementById('gen_description').value
    };

    try {
        const res = await fetch(`${API_BASE_URL}/invitation/generate`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(requestData)
        });

        if (res.status === 201) {
            // Serverul returnează numele fișierului (ex: invitatie_123.pdf)
            const fileName = await res.text();
            const downloadUrl = `http://localhost:8080/output/${fileName}`;

            // Schimbăm textul cu un buton real de download
            messageElement.innerHTML = `
                <div style="margin-top:20px; padding:15px; background: rgba(75, 181, 67, 0.2); border-radius:10px; border: 1px solid #4BB543;">
                    <p style="color: #4BB543; font-weight: bold; margin-bottom:10px;">✅ Invitație generată cu succes!</p>
                    <a href="${downloadUrl}" target="_blank"
                       style="display: inline-block; background: #4BB543; color: white; padding: 10px 20px; border-radius: 25px; text-decoration: none; font-weight: bold;">
                       DESCARCĂ PDF
                    </a>
                </div>`;
        } else {
            const err = await res.text();
            messageElement.innerText = "Eroare: " + err;
            messageElement.style.color = "#ff6b6b";
        }
    } catch (e) {
        messageElement.innerText = "Eroare la generare!";
        messageElement.style.color = "#ff6b6b";
    }
});