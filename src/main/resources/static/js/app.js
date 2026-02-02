class MicroFuzzer {
    constructor() {
        this.currentTab = 'upload';
        this.init();
    }

    init() {
        this.bindEvents();
        this.showTab('upload');
        this.loadHistory();
    }

    bindEvents() {
        // Tab switching
        document.querySelectorAll('.tab').forEach(tab => {
            tab.addEventListener('click', (e) => {
                this.showTab(e.target.dataset.tab);
            });
        });

        // File upload
        document.getElementById('uploadForm').addEventListener('submit', (e) => {
            this.handleFileUpload(e);
        });

        // Text fix
        document.getElementById('textForm').addEventListener('submit', (e) => {
            this.handleTextFix(e);
        });

        // Load history
        document.getElementById('loadHistory').addEventListener('click', () => {
            this.loadHistory();
        });
    }

    showTab(tabName) {
        // Update active tab
        document.querySelectorAll('.tab').forEach(tab => {
            tab.classList.remove('active');
        });
        document.querySelector(`[data-tab="${tabName}"]`).classList.add('active');

        // Update active content
        document.querySelectorAll('.tab-content').forEach(content => {
            content.classList.remove('active');
        });
        document.getElementById(`${tabName}Tab`).classList.add('active');

        this.currentTab = tabName;
    }

    async handleFileUpload(e) {
        e.preventDefault();
        const formData = new FormData(e.target);
        
        try {
            const response = await fetch('/upload', {
                method: 'POST',
                body: formData
            });
            
            const html = await response.text();
            this.updateResults(html);
            this.loadHistory();
        } catch (error) {
            console.error('Error:', error);
        }
    }

    async handleTextFix(e) {
        e.preventDefault();
        const formData = new FormData(e.target);
        
        try {
            const response = await fetch('/fix-text', {
                method: 'POST',
                body: formData
            });
            
            const html = await response.text();
            this.updateResults(html);
            this.loadHistory();
        } catch (error) {
            console.error('Error:', error);
        }
    }

    async loadHistory() {
        const username = document.querySelector('input[name="username"]').value;
        
        try {
            const response = await fetch(`/history?username=${username}`);
            const html = await response.text();
            const parser = new DOMParser();
            const doc = parser.parseFromString(html, 'text/html');
            const historySection = doc.querySelector('.file-history');
            
            if (historySection) {
                document.querySelector('.file-history').innerHTML = historySection.innerHTML;
                this.bindHistoryEvents();
            }
        } catch (error) {
            console.error('Error loading history:', error);
        }
    }

    bindHistoryEvents() {
        document.querySelectorAll('.file-item').forEach(item => {
            item.addEventListener('click', () => {
                const fileId = item.dataset.fileId;
                this.loadFile(fileId);
            });
        });
    }

    async loadFile(fileId) {
        try {
            const response = await fetch(`/file/${fileId}`);
            const file = await response.json();
            
            document.getElementById('originalContent').textContent = file.originalContent;
            document.getElementById('fixedContent').textContent = file.fixedContent;
            document.getElementById('errors').textContent = file.errorsFound;
            document.getElementById('errorCount').textContent = `Found ${file.errorsFound.split('\n').filter(e => e.trim()).length} errors`;
            
            this.showTab('results');
        } catch (error) {
            console.error('Error loading file:', error);
        }
    }

    updateResults(html) {
        const parser = new DOMParser();
        const doc = parser.parseFromString(html, 'text/html');
        
        const results = doc.querySelector('.results');
        if (results) {
            document.querySelector('.results').innerHTML = results.innerHTML;
            this.showTab('results');
        }
    }

    downloadFixedContent() {
        const fixedContent = document.getElementById('fixedContent').textContent;
        const blob = new Blob([fixedContent], { type: 'text/plain' });
        const url = URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = 'fixed_file.txt';
        document.body.appendChild(a);
        a.click();
        document.body.removeChild(a);
        URL.revokeObjectURL(url);
    }
}

// Initialize application when DOM is loaded
document.addEventListener('DOMContentLoaded', () => {
    new MicroFuzzer();
});