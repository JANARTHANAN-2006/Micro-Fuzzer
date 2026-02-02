
## 🚀 Overview

**Micro-Fuzzer** is a high-performance specialized logic engine built for automated source code sanitization. In a landscape where code quality is non-negotiable, this tool acts as a **"Syntax Firewall,"** utilizing a strict deterministic framework to ensure 100% predictable outcomes without the "hallucinations" common in modern probabilistic AI models.



---

## 🧠 Deterministic Logic vs. Generative AI

While Generative AI is a powerful creative partner, Micro-Fuzzer is your **Compliance Guardian**. It trades probabilistic "guessing" for mathematical certainty.

| Feature | 🤖 Generative AI (LLMs) | 🛡️ Micro-Fuzzer (Deterministic) |
| :--- | :--- | :--- |
| **Reliability** | Probabilistic; may "hallucinate" | 100% Accuracy; strictly rule-based |
| **Speed** | High latency (API/Inference time) | Sub-millisecond (Instant execution) |
| **Privacy** | Often requires cloud connectivity | 100% Offline; Zero data leaks |
| **Auditability** | Black-box decision making | Full H2 SQL audit logs for forensics |
| **Security** | Risk of introducing "lazy" patterns | Prevents bad patterns before compilation |

---

## ⚡ Key Technical Features

* **🔍 Precision Scanning:** Uses optimized Regex-based logic for real-time detection of missing delimiters, mismatched brackets, and keyword corruption.
* **📜 Integrity Logging:** Automatically records every modification in an **embedded H2 SQL Database**, providing a permanent audit trail for security compliance.
* **🔒 Zero-Trust Execution:** Operates entirely within your local environment; your source code never leaves your secure machine.
* **🎨 Industrial UI:** A high-contrast, low-latency Java Swing interface designed for long-form professional development sessions.

---

## 💎 Significance of the Software

Micro-Fuzzer addresses the **"Last Mile"** of development by automating the tedious task of syntax correction, allowing engineers to focus on high-level architecture rather than structural debugging.

1.  **CI/CD Pre-processor:** Catching syntax errors that would otherwise break expensive build pipelines.
2.  **Secure Enclaves:** Providing advanced code-repair capabilities in air-gapped environments where AI tools are restricted.
3.  **Educational Standard:** Helping developers visualize structural errors through a clear, logged repair process.

---

## 📦 Installation & Usage

### Prerequisites
* **Java Runtime:** version 17 or higher
* **Build Tool:** Maven (if building from source)

### Quick Start
```bash
# 1. Clone the repository
git clone [https://github.com/JANARTHANAN-2006/micro-fuzzer.git](https://github.com/JANARTHANAN-2006/micro-fuzzer.git)

# 2. Navigate to the project directory
cd micro-fuzzer

# 3. Build the executable
mvn clean package

# 4. Launch the Guardian
java -jar target/micro-fuzzer-1.0.jar 
