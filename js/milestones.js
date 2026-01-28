let totalTokens;
let userID = localStorage.getItem('userID');
document.addEventListener('DOMContentLoaded', async () => {
    
    async function fetchIncentiveData(userID) {
        try {
            // Fetch all incentives
            const response = await fetch("https://studymileswebapp.onrender.com/incentive");
            if (!response.ok) {
                throw new Error("Failed to fetch incentive data");
            }

            const data = await response.json();
            const result = data.find(item => item.userID.userID === parseInt(userID));
            if (result) {
                const incentiveID = result.incentivesID;
                const incentiveResponse = await fetch(`https://studymileswebapp.onrender.com/incentive/${incentiveID}`);
                if (!incentiveResponse.ok) {
                    throw new Error("Failed to fetch incentive details");
                }

                const incentiveData = await incentiveResponse.json();

                totalTokens = incentiveData.earnedTokens;
                document.getElementById('tokenCount').textContent = totalTokens; // Update the token counter in the UI
                return incentiveData; // Return the incentive data for further use
            } else {
                console.log("User not found in incentive data. while fetching");
                return null; // Return null if no incentive is found
            }
        } catch (error) {
            console.error("Error fetching incentive data:", error);
            return null; // Return null in case of an error
        }
    }

    fetchIncentiveData(userID);
    
    const optionsContainer = document.querySelector('.options');
    const milesContainer = document.querySelector('.miles');

    if (!userID) {
        alert("No user ID found. Please log in again.");
        window.location.href = "login.html";
        return;
    }
    
    try {
        const response = await fetch(`https://studymileswebapp.onrender.com/new_user/${userID}`);
        
        if (!response.ok) {
            throw new Error("Failed to fetch user data");
        }

        const userData = await response.json();

        document.querySelector("#userName").textContent = userData.name;

    } catch (error) {
        console.error("Error fetching user data:", error);
    }

    const ongoingContent = `
        <div class="mile">
            <p>Complete the assignment before deadline</p>
            <div class="img">
                +2<img src="img/icons/token.png" alt="" class="tokImg">
            </div>
        </div>
        <div class="mile">
            <p>Conquer Planet Mars</p>
            <div class="img">
                +2<img src="img/icons/token.png" alt="">
            </div>
        </div>
        <div class="mile">
            <p>Day 3 Streak</p>
            <div class="img">
                +2<img src="img/icons/token.png" alt="">
            </div>
        </div>
    `;


    const completedContent = `
        <div class="mile">
            <p>Completed Challenge 1: Finish the first lesson</p>
            <div class="img">
                +3<img src="img/icons/token.png" alt="">
            </div>
        </div>
        <div class="mile">
            <p>Completed Challenge 2: Day 2 Streak</p>
            <div class="img">
                +5<img src="img/icons/token.png" alt="">
            </div>
        </div>
        
    `;


    milesContainer.innerHTML = ongoingContent;


    optionsContainer.addEventListener('click', (e) => {
        if (e.target.tagName === 'H3') {
            // Toggle classes for visual indication
            const options = optionsContainer.querySelectorAll('h3');
            options.forEach(item => {
                item.classList.remove('select');
                item.classList.add('unselect');
            });
            e.target.classList.remove('unselect');
            e.target.classList.add('select');

            // Change the content based on the selected option
            if (e.target.textContent.trim() === "Ongoing Challenges") {
                milesContainer.innerHTML = ongoingContent;
            } else if (e.target.textContent.trim() === "Completed") {
                milesContainer.innerHTML = completedContent;
            }
        }
    });
    const menuToggle = document.getElementById('menuToggle');
    const nav = document.querySelector('.nav');

    menuToggle.addEventListener('click', () => {
        nav.classList.toggle('active'); // Toggle the 'active' class on the nav bar
    });
});