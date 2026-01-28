import { LESes } from './data.js';

let userID = localStorage.getItem('userID');
let completedPercentage = [0, 0, 0];
let totalTokens;
let seconds = 0;
let takenSeconds = {
    section1: [0, 0, 0],
    section2: [0, 0, 0],
    section3: [0, 0, 0]
};

document.addEventListener("DOMContentLoaded", async function () {
    function update() {
        const storedCompleted = JSON.parse(localStorage.getItem('completedPercentage'));
        completedPercentage = Array.isArray(storedCompleted) ? storedCompleted : [0, 0, 0];

        seconds = parseInt(localStorage.getItem('seconds')) || 0;
        takenSeconds = JSON.parse(localStorage.getItem('takenSeconds')) || {
            section1: [0, 0, 0],
            section2: [0, 0, 0],
            section3: [0, 0, 0]
        };

        // Ensure the percentage is never undefined or malformed
        completedPercentage = completedPercentage.map(p => (typeof p === 'number' && !isNaN(p)) ? p : 0);
    }

    async function fetchIncentiveData(userID) {
        try {
            const response = await fetch("https://studymileswebapp.onrender.com/incentive");
            if (!response.ok) throw new Error("Failed to fetch incentive data");

            const data = await response.json();
            const result = data.find(item => item.userID.userID === parseInt(userID));
            if (result) {
                const incentiveID = result.incentivesID;
                const incentiveResponse = await fetch(`https://studymileswebapp.onrender.com/incentive/${incentiveID}`);
                if (!incentiveResponse.ok) throw new Error("Failed to fetch incentive details");

                const incentiveData = await incentiveResponse.json();
                totalTokens = incentiveData.earnedTokens;
                document.getElementById('tokenCount').textContent = totalTokens;
                return incentiveData;
            } else {
                console.log("User not found in incentive data. while fetching");
                return null;
            }
        } catch (error) {
            console.error("Error fetching incentive data:", error);
            return null;
        }
    }

    if (!userID) {
        alert("No user ID found. Please log in again.");
        window.location.href = "login.html";
        return;
    }

    try {
    const response = await fetch(`https://studymileswebapp.onrender.com/new_user/${userID}`);
    if (!response.ok) throw new Error("Failed to fetch user data");
    const userData = await response.json();
    document.querySelector("#userName").textContent = userData.name;

    // Reset to 0% if new user BEFORE update()
    if (userData.isNewUser) {
        completedPercentage = [0, 0, 0];
        seconds = 0;
        takenSeconds = {
            section1: [0, 0, 0],
            section2: [0, 0, 0],
            section3: [0, 0, 0]
        };
        localStorage.setItem('completedPercentage', JSON.stringify(completedPercentage));
        localStorage.setItem('seconds', seconds);
        localStorage.setItem('takenSeconds', JSON.stringify(takenSeconds));
    } else {
        update(); // Only update from localStorage if user is NOT new
    }
} catch (error) {
    console.error("Error fetching user data:", error);
}


    update();
    fetchIncentiveData(userID);

    const percentages = document.querySelectorAll(".compStat");
    percentages.forEach((percentage, index) => {
        let capped = Math.min(completedPercentage[index], 100);
        percentage.textContent = "completed: " + capped + "%";
    });

    const tokens = document.querySelector('.tokenCount');
    tokens.textContent = totalTokens;

    const lessons = document.querySelectorAll(".title");
    lessons.forEach((lesson, lessonIndex) => {
        const colorimg = lesson.querySelector('.color-image');
        let percent = Math.min(completedPercentage[lessonIndex], 100);
        colorimg.style.width = percent + '%';
        let CP = 100 - percent;
        colorimg.style.clipPath = `inset(0 ${CP}% 0 0)`;
    });

    const display = document.getElementById('display');
    function updateDisplay() {
        const hrs = String(Math.floor(seconds / 3600)).padStart(2, '0');
        const mins = String(Math.floor((seconds % 3600) / 60)).padStart(2, '0');
        const secs = String(seconds % 60).padStart(2, '0');
        display.textContent = `${hrs}:${mins}:${secs}`;
    }
    updateDisplay();

    const historyButton = document.querySelector('.historylink'); 
    const historyContainer = document.querySelector('.hitoryCont'); 
    const closebtn = document.querySelector('.closeHistory'); 

    historyButton.addEventListener('click', () => {
        historyContainer.style.display = 'block';
    });

    closebtn.addEventListener('click', () => {
        historyContainer.style.display = 'none';
    });

    const historyItems = document.querySelectorAll('.historyItem');
    historyItems.forEach((history, i) => {
        const h61 = document.createElement('h6');
        const h62 = document.createElement('h6');
        const h63 = document.createElement('h6');

        const span1 = document.createElement('span');
        const span2 = document.createElement('span');
        const span3 = document.createElement('span');

        const sectionKey = `section${i + 1}`;
        const lessons = LESes[sectionKey];

        const lessonData = lessons?.[0]?.title ? lessons[0] : null;
        const lessonData2 = lessons?.[1]?.title ? lessons[1] : null;
        const lessonData3 = lessons?.[2]?.title ? lessons[2] : null;

        const formatTime = (seconds) => {
            const hrs = String(Math.floor(seconds / 3600)).padStart(2, '0');
            const mins = String(Math.floor((seconds % 3600) / 60)).padStart(2, '0');
            const secs = String(seconds % 60).padStart(2, '0');
            return `${hrs}:${mins}:${secs}`;
        };

        span1.textContent = formatTime(takenSeconds[`section${i + 1}`][0]);
        span2.textContent = formatTime(takenSeconds[`section${i + 1}`][1]);
        span3.textContent = formatTime(takenSeconds[`section${i + 1}`][2]);

        span1.classList.add('timer');
        span2.classList.add('timer');
        span3.classList.add('timer');

        h61.textContent = lessonData ? lessonData.title : 'No data available';
        h61.appendChild(span1);
        h62.textContent = lessonData2 ? lessonData2.title : 'No data available';
        h62.appendChild(span2);
        h63.textContent = lessonData3 ? lessonData3.title : 'No data available';
        h63.appendChild(span3);

        history.appendChild(h61);
        history.appendChild(h62);
        history.appendChild(h63);
    });

    const menuToggle = document.getElementById('menuToggle');
    const nav = document.querySelector('.nav');

    menuToggle.addEventListener('click', () => {
        nav.classList.toggle('active');
    });
});
