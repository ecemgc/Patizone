ALTER TABLE ad_types
ADD text VARCHAR(400) NOT NULL;
INSERT INTO ad_types (NAME, TEXT)
VALUES (
        'WALKING',
        'Walking. (I want my dog to be taken for a walk.)'
    );
INSERT INTO ad_types (NAME, TEXT)
VALUES (
        'TEMPORARY_ADOPTING',
        'Temporary Adopting. (I want to take care of a pet for a short time.)'
    );
INSERT INTO ad_types (NAME, TEXT)
VALUES (
        'PERMANENT_ADOPTING',
        'Permanent Adopting. (I want to adopt a pet permanently.)'
    );
INSERT INTO ad_types (NAME, TEXT)
VALUES (
        'TEMPORARY_HOSTING',
        'Temporary Hosting. (I need someone to take care of my pet while I''m away.)'
    );
INSERT INTO ad_types (NAME, TEXT)
VALUES (
        'FOUND_PET',
        'Found Pet. (I found a lost pet and I am looking for the owner.)'
    );
INSERT INTO ad_types (NAME, TEXT)
VALUES (
        'LOST_PET',
        'Lost Pet. (My pet is missing, and I need help finding it.)'
    );
INSERT INTO ad_types (NAME, TEXT)
VALUES (
        'PET_SITTING',
        'Pet Sitting. (I want someone to take care of my pet at my home.)'
    );
INSERT INTO ad_types (NAME, TEXT)
VALUES (
        'VETERINARY_HELP',
        'Veterinary Help. (I need assistance for my pet''s veterinary treatment.)'
    );
INSERT INTO ad_types (NAME, TEXT)
VALUES (
        'PET_MATING',
        'Pet Mating. (I am looking for a mate for my pet.)'
    );
INSERT INTO ad_types (NAME, TEXT)
VALUES (
        'DONATION',
        'Donation. (I am collecting donations for stray animals or pets in need.)'
    );
INSERT INTO ad_types (NAME, TEXT)
VALUES (
        'PET_TRANSPORT',
        'Pet Transport. (I need help transporting a pet to another location.)'
    );
INSERT INTO ad_types (NAME, TEXT)
VALUES ('OTHER', 'Other.');