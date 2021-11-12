# York Pirates!

## Table of Contents
1. [About this project](#about-this-project)
    - [What is it?](#what-is-it)
    - [The team](#the-team)
2. [Resources](#resources)
    - [Using Git](#using-git)
3. [Appendices](#appendices)
    - [Appendix A: Product brief](#product-brief)

## About this project

### What is it?

We have to make a game together. The game is about pirates sailing a large lake, around which is built an alternate-universe University of York, and battling other ships. The full brief is included in [Appendix A](#product-brief).

### The team

This project was started by Cohort 1, Team 2 of the 2021/22 ENG1 module. The members of that team are:

- Will Burden ([@willburden](http://github.com/willburden))
- Kieron Jones
- Ruiqi Liu
- Adam Nicholson
- Tabitha Oldfield
- Camran Qadeer

## Resources

### Using Git

Here are some helpful guides to Git:

- [the simple guide][1]
- ['About Git' on the Github Docs][2]

You have two options when working with Git: either use the command line like a pro (see below), or use some kind of GUI (e.g. most IDEs including VSCode have built in Git support).

#### Command line guide:

To set up the project:

1. Make sure you have Git installed. Check [the simple guide][1] for more on this.

2. Open Git Bash if you're on Windows, or just open the terminal if you're on Linux.

3. `cd` into the parent directory where you want to place your copy of the project. For example, if you wanted the project to be located in `~/Projects/York-Pirates`, you would type `cd ~/Projects`.

4. Type `git clone https://github.com/willburden/York-Pirates.git` to create a local version of the repository in a folder called York-Pirates on your computer.

5. You can now edit the files normally and make commits/push to the repository (see below).

To push your changes to the shared repository:

1. `cd` to the project root, i.e. the `York-Pirates` directory.

2. Type `git add .` This 'stages' all your changes by copying them into the index (the set of files that will be committed).

3. Type `git commit -m "Enter a commit message here"`. This creates a new commit with your changes.

4. Type `git push`. This pushes your commits to the shared, Github-hosted repo. (It may tell you to set up your details, if so just do what it says.)

For more advanced usage, read the above guides, or use Google.

**IMPORTANT NOTE**: You generally shouldn't push directly to the `main` branch. Instead, create and checkout a new branch with `git checkout -b <branch-name>`, commit your changes to this new branch, and `git push --set-upstream origin <branch-name>` to create the new branch on the shared repo. You can then create a pull request on the Github website to have your changes merged into the `main` branch. This is to ensure we can check the changes are correct before merging into `main`.

## Appendices

### Appendix A: Product Brief<a name="product-brief"></a>

*Copied from [the VLE][3]:*

> ### York Pirates!
> 
> In an alternative Universe, the University of York is built around a very large lake, on which you can find a number of colleges. Ships are the only manner of transport to go from college to college or building to building; some colleges may be friendly to each other, others may be hostile to each other.
> 
> You are a privateer with allegiance to one College (e.g., Constantine). You sail the great Lake of York on quests and adventures, and may choose to attack ships of other Colleges, try to sack Colleges, etc. Gameplay ends when the player is defeated in some way (e.g., in combat with another privateer, or destroyed by a rival College), or when the player has achieved the game’s objective (see below). Specific features that are required include:
> 
> - The player may engage in combat with another ship or College.
> - There must be at least three colleges in the game.
> - Points must be accumulated through combat and via the passage of time (e.g., sailing through bad weather without sinking may accumulate more points than simply sailing through good weather). Defeating another pirate ship will lead to the acquisition of plunder (i.e., gold).
> - It must be possible to capture another collevia combat; successfully capturing a college will lead to additional points. Capturing a college may lead to the acquisition of plunder (i.e., gold).
> - Each game play should have an objective (e.g., defeat the Chief Pirate of James
> College). The objective should not be immediately achievable (i.e., there should be tasks that need to be completed first).
> - There should be a way to “spend” the plunder (e.g., gold) acquired, e.g., to repair a ship, to acquire new provisions or weapons etc.
> 
> ### Constraints
> You are building a game that should be playable and enjoyable by your Engineering 1 cohort. However, there are two stakeholders that you must also accommodate.
> 
> - The customer: one of your lecturers will play the role of a customer who is interested in eventually trying to market and sell your game. Ultimately the customer is the person you must convince of the validity of your assumptions and decisions. This stakeholder can be contacted as often as you need and at any time (but do not expect an instant reply!).
> - The University of York Communications Office: who is interested in using your game for its own promotional activities, e.g., at Open Days, UCAS Days. Please note that you can only communicate with this stakeholder through the lecturers.
>
> ### For Assessment 1:
> 
> Implement all the features described above **excluding** the following.
>
> - Obstacles and bad weather
> - Ways to spend the plunder
> - Combat with other ships
>
> ### For Assessment 2:
>
> Implement the full product brief.

[1]: https://rogerdudler.github.io/git-guide/ "git - the simple guide"

[2]: https://docs.github.com/en/get-started/using-git/about-git "About Git - Github Docs"

[3]: https://vle.york.ac.uk/bbcswebdav/pid-4024073-dt-content-rid-12763959_2/courses/Y2021-014161/ENG1%20Product%20Brief%202021%20-%20York%20Pirates%21%281%29.pdf "Product Brief"
