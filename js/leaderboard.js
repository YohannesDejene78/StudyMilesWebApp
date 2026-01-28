document.addEventListener('DOMContentLoaded', async () => {
    let totalTokens;
    let userID = localStorage.getItem('userID');
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

    async function fetchIncentives() {
        try {
            // Fetch all incentive data
            const response = await fetch("https://studymileswebapp.onrender.com/incentive");
            if (!response.ok) {
                throw new Error("Failed to fetch incentive data");
            }

            const data = await response.json();

            // Sort the data by earnedTokens in descending order
            const sortedData = data.sort((a, b) => b.earnedTokens - a.earnedTokens);

            // Map through the sorted incentive data and generate the leaderboard content
            const alltimeContent = sortedData.map((item, index) => {
                // Determine the rank image or number
                let rankDisplay;
                if (index === 0) {
                    rankDisplay = `<img src="img/lead1.png">`;
                } else if (index === 1) {
                    rankDisplay = `<img src="img/lead2.png">`;
                } else if (index === 2) {
                    rankDisplay = `<img src="img/lead3.png">`;
                } else {
                    rankDisplay = `<p>${index + 1}</p>`; // Display rank as a number for 4th and beyond
                }

                return `
                    <div class="mile">
                        <div class="pfpInfo">
                            ${rankDisplay}
                            <img src="img/icon2/profile3.svg">
                            <p>${item.userID.name}</p>
                        </div>
                        <div class="img">
                            <div class="tokenEarned">
                                <img src="img/icons/token.png">
                                <p>Token Earned: ${item.earnedTokens}</p>
                            </div>
                        </div>
                    </div>
                `;
            }).join(""); // Join all the mapped content into a single string

            // Update the leaderboard container
            const milesContainer = document.querySelector('.miles');
            milesContainer.innerHTML = alltimeContent;

        } catch (error) {
            console.error("Error fetching incentive data:", error);
        }
    }

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

    // Fetch and display incentives
    await fetchIncentives();
    await fetchIncentiveData(userID);
    // Update total tokens
    
    const tokens = document.querySelector('.tokenCount');
    tokens.textContent = totalTokens;

    // Menu toggle functionality
    const menuToggle = document.getElementById('menuToggle');
    const nav = document.querySelector('.nav');

    menuToggle.addEventListener('click', () => {
        nav.classList.toggle('active'); // Toggle the 'active' class on the nav bar
    });
});